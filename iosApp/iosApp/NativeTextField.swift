import SwiftUI
import ComposeApp

class IOSNativeTextField: NativeTextViewFactory {
    static var shared = IOSNativeTextField()

    func createTextFieldView(valor: String, onValueChange: @escaping (String) -> Void, label: String) -> UIViewController {
        let view = NativeTextField(valor: valor, action: onValueChange, label: label)
        return UIHostingController(rootView: view)
    }
}


struct NativeTextField: UIViewRepresentable {
    var valor: String
    var action: (String) -> Void
    var label: String
    
    class Coordinator: NSObject, UITextFieldDelegate {
        var parent: NativeTextField
        
        init(parent: NativeTextField) {
            self.parent = parent
        }
        
        func textFieldDidChangeSelection(_ textField: UITextField) {
            parent.valor = textField.text ?? ""
            parent.action(parent.valor)
        }
    }
    
    func makeCoordinator() -> Coordinator {
        Coordinator(parent: self)
    }
    
    func makeUIView(context: Context) -> UIView {
        let view = UIView()
    
        let label = UILabel()
        label.text = self.label
        label.textColor = .white
        label.font = UIFont.boldSystemFont(ofSize: 12)
        label.translatesAutoresizingMaskIntoConstraints = false

        let textField = UITextField()
        textField.delegate = context.coordinator
        textField.text = valor
        textField.keyboardType = .decimalPad
        textField.textColor = .white
        textField.backgroundColor = UIColor(white: 1, alpha: 0.25)
        textField.layer.cornerRadius = 12
        textField.layer.borderWidth = 2
        textField.layer.borderColor = UIColor.gray.cgColor
        textField.translatesAutoresizingMaskIntoConstraints = false
    
        // Agregar padding al inicio del UITextField
        let paddingView = UIView(frame: CGRect(x: 0, y: 0, width: 10, height: 60))
        textField.leftView = paddingView
        textField.leftViewMode = .always
        
        view.addSubview(label)
        view.addSubview(textField)

        NSLayoutConstraint.activate([
            label.topAnchor.constraint(equalTo: view.topAnchor),
                           label.leadingAnchor.constraint(equalTo: view.leadingAnchor),
                           label.trailingAnchor.constraint(equalTo: view.trailingAnchor),
                           textField.topAnchor.constraint(equalTo: label.bottomAnchor, constant: 6),
                           textField.leadingAnchor.constraint(equalTo: view.leadingAnchor),
                           textField.trailingAnchor.constraint(equalTo: view.trailingAnchor),
                           textField.heightAnchor.constraint(equalToConstant: 60),
                           textField.bottomAnchor.constraint(equalTo: view.bottomAnchor)
        ])
    
        return view
    }
    
    func updateUIView(_ uiView: UIView, context: Context) {
        if let textField = uiView.subviews.last as? UITextField {
            textField.text = valor
            textField.layer.borderColor = context.environment.isFocused ? UIColor.blue.cgColor : UIColor.gray.cgColor
        }
    }
}



//imagen de teclado
//Image(systemName: "keyboard.chevron.compact.down")

