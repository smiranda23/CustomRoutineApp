package org.basicfactorysm.data

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.basicfactory.db.DatabaseBF


actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = DatabaseBF.Schema,
            context = context,
            name = "DatabaseBF.db"
        )
    }
}