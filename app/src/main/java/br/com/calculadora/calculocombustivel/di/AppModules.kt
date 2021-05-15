package br.com.calculadora.calculocombustivel.di

import android.widget.SpinnerAdapter
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.calculadora.calculocombustivel.business.CalculoCombustivel
import br.com.calculadora.calculocombustivel.business.ResumoGastosBusiness
import br.com.calculadora.calculocombustivel.database.AppDatabase
import br.com.calculadora.calculocombustivel.database.dao.ReabastecimentoDAO
import br.com.calculadora.calculocombustivel.model.Reabastecimento
import br.com.calculadora.calculocombustivel.repository.ReabastecimentoRepository
import br.com.calculadora.calculocombustivel.ui.fragment.ResumoFragment
import br.com.calculadora.calculocombustivel.ui.recyclerview.adapter.ListaReabastecimentoAdapter
import br.com.calculadora.calculocombustivel.ui.viewmodel.AppViewModel
import br.com.calculadora.calculocombustivel.ui.viewmodel.CadastroViewModel
import br.com.calculadora.calculocombustivel.ui.viewmodel.ListaReabastecimentoViewModel
import br.com.calculadora.calculocombustivel.ui.viewmodel.ResumoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val NOME_BASE = "calculocombustivel.db"
private const val NOME_DB_TESTE = "calculocombustivel_teste.db"

val dbTesteModule = module{
    single<AppDatabase>{
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            NOME_DB_TESTE
        ).addCallback(object: RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                CoroutineScope(IO).launch {
                    val dao: ReabastecimentoDAO by inject()
                    repeat(10 ) {

                        dao.salva(
                            Reabastecimento(
                                tipoCombustivel = "Gasolina",
                                kmRodados = 100.0 + it,
                                litros = 100.0 + it,
                                consumoKilometrosPorLitro = 10.0 + it,
                                posicaoTipoDeCombustivel = 0,
                                dataGravacao = "2020-0$it-0$it",
                                valor = "100.0"
                            )
                        )
                    }
                    dao.salva(Reabastecimento(
                        tipoCombustivel = "Gasolina",
                        kmRodados = 3000.0,
                        litros = 50.0,
                        consumoKilometrosPorLitro = 12.0,
                        posicaoTipoDeCombustivel = 0,
                        dataGravacao = "2020-02-03",
                        valor = "167.0"
                        ))
                    dao.salva(Reabastecimento(
                        tipoCombustivel = "Gasolina",
                        kmRodados = 200.0,
                        litros = 400.0,
                        consumoKilometrosPorLitro = 12.0,
                        posicaoTipoDeCombustivel = 0,
                        dataGravacao = "2020-02-03",
                        valor = "87.0"
                        ))
                    dao.salva(Reabastecimento(
                        tipoCombustivel = "Gasolina",
                        kmRodados = 100.0,
                        litros = 10.0,
                        consumoKilometrosPorLitro = 12.0,
                        posicaoTipoDeCombustivel = 0,
                        dataGravacao = "2020-02-03",
                        valor = "77.0"
                        ))
                    dao.salva(Reabastecimento(
                        tipoCombustivel = "Gasolina",
                        kmRodados = 200.0,
                        litros = 400.0,
                        consumoKilometrosPorLitro = 12.0,
                        posicaoTipoDeCombustivel = 0,
                        dataGravacao = "2020-04-08",
                        valor = "200.0"
                    ))
                    dao.salva(Reabastecimento(
                        tipoCombustivel = "Gasolina",
                        kmRodados = 200.0,
                        litros = 400.0,
                        consumoKilometrosPorLitro = 12.0,
                        posicaoTipoDeCombustivel = 0,
                        dataGravacao = "2020-04-12",
                        valor = "300.0"

                    ))
                    dao.salva(Reabastecimento(
                        tipoCombustivel = "Gasolina",
                        kmRodados = 200.0,
                        litros = 400.0,
                        consumoKilometrosPorLitro = 12.0,
                        posicaoTipoDeCombustivel = 0,
                        dataGravacao = "2020-04-14",
                        valor = "100.0"
                    ))
                }
            }

        }).build()
    }
}

val dbModule = module {

    single<AppDatabase>{
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            NOME_BASE
        ).build()
    }


}

val uiModule = module{
    factory<ListaReabastecimentoAdapter> { ListaReabastecimentoAdapter(get(),get()) }
}

val daoModule = module{
    single<ReabastecimentoDAO>{get<AppDatabase>().dao}
}

val repositoryModule = module{
    single<ReabastecimentoRepository>{ ReabastecimentoRepository(get()) }
}

val businessModule = module{
    single<CalculoCombustivel> {  CalculoCombustivel() }
    single<ResumoGastosBusiness>{ ResumoGastosBusiness() }
}

val viewModule = module{
    viewModel<ListaReabastecimentoViewModel>{ ListaReabastecimentoViewModel(get()) }
    viewModel<CadastroViewModel>{ CadastroViewModel(get())}
    viewModel<AppViewModel> { AppViewModel() }
    viewModel<ResumoViewModel> { ResumoViewModel(get()) }
}

val appModule = listOf(
    dbModule, uiModule, repositoryModule, viewModule, daoModule, businessModule
)

val testeAppModule = listOf(
    dbTesteModule, uiModule, repositoryModule, viewModule, daoModule, businessModule
)