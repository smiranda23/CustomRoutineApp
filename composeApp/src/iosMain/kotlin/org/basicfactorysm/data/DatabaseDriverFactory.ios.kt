package org.basicfactorysm.data

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.basicfactory.db.DatabaseBF

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            schema = DatabaseBF.Schema,
            name = "DatabaseBF.db"
        )
    }
}