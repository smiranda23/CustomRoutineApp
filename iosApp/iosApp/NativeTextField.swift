//
//  NativeTextField.swift
//  iosApp
//
//  Created by Sebastián Miranda Rivera on 12/3/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import SwiftUI
import ComposeApp

class IOSNativeTextField: NativeTextViewFactory {
    static var shared = IOSNativeTextField()
    func createTextFieldView(label: String, onClick: @escaping () -> Void) -> UIViewController {
        let view = NativeTextField(label: label, action: onClick)
        
        return UIHostingController(rootView: view)
    }
    
}

struct NativeTextField: View {
    var label: String
    var action: () -> Void
    
    var body: some View {
        Button(action: action) {
            Text(label).font(.headline)
        }
    }
}
