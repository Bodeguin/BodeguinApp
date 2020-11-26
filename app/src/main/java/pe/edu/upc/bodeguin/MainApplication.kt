package pe.edu.upc.bodeguin

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import pe.edu.upc.bodeguin.data.network.interceptor.NetworkConnectionInterceptor
import pe.edu.upc.bodeguin.data.network.service.AppService
import pe.edu.upc.bodeguin.data.persistance.database.AppDatabase
import pe.edu.upc.bodeguin.data.repository.*
import pe.edu.upc.bodeguin.ui.viewModel.authentication.AuthViewModelFactory
import pe.edu.upc.bodeguin.ui.viewModel.cart.CartViewModelFactory
import pe.edu.upc.bodeguin.ui.viewModel.category.CategoryViewModelFactory
import pe.edu.upc.bodeguin.ui.viewModel.product.ProductViewModelFactory
import pe.edu.upc.bodeguin.ui.viewModel.profile.UserViewModelFactory
import pe.edu.upc.bodeguin.ui.viewModel.store.StoreViewModelFactory

class MainApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@MainApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { AppService.invoke(instance<NetworkConnectionInterceptor>()) }
        bind() from provider { AppDatabase(instance()) }
        bind() from singleton { CartRepository(instance(), instance()) }
        bind() from singleton { CategoryRepository(instance()) }
        bind() from singleton { ProductRepository(instance()) }
        bind() from singleton { StoreRepository(instance()) }
        bind() from singleton { UserRepository(instance(), instance()) }
        bind() from provider { AuthViewModelFactory(instance(), instance()) }
        bind() from provider { CartViewModelFactory(instance(), instance()) }
        bind() from provider { UserViewModelFactory(instance(), instance()) }
        bind() from provider { CategoryViewModelFactory(instance(), instance()) }
        bind() from provider { ProductViewModelFactory(instance(), instance()) }
        bind() from provider { StoreViewModelFactory(instance(), instance()) }

    }
}