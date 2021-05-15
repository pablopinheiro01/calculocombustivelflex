package br.com.calculadora.calculocombustivel.ui


import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import br.com.calculadora.calculocombustivel.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

class AdicionaCombustivelScreenTest {

    @Rule
    @JvmField
    var activity = ActivityTestRule(MainActivity::class.java,true,false)

    @Test
    fun deve_AdicionarUmNovoItem_QuandoClicaEmAdicionar() {

        activity.launchActivity(Intent())

        onView(allOf(withId(R.id.lista_float_action_button), isDisplayed())).perform(click())
        onView(allOf(withId(R.id.cadastro_itens_lista_combustivel), isDisplayed())).perform(click())
        onData(`is`("Diesel")).inRoot(isPlatformPopup()).perform(click())
        onView(allOf(withId(R.id.cadastro_item_km_rodados), isDisplayed())).perform(
            click(),
            typeText("250"),
            closeSoftKeyboard()
        )
        onView(allOf(withId(R.id.cadastro_item_quantidade_litros), isDisplayed())).perform(
            click(),
            typeText("250"),
            closeSoftKeyboard()
        )
        onView(allOf(withId(R.id.cadastro_itens_botao_salvar), isDisplayed())).perform(click())
        pressBack()

    }

    @Test
    fun deve_VerItemCadastradoNoFimDaLista_QuandoFinalizarAdicao(){
        activity.launchActivity(Intent())

        onView(allOf(withId(R.id.lista_float_action_button), isDisplayed())).perform(click())
        onView(allOf(withId(R.id.cadastro_itens_lista_combustivel), isDisplayed())).perform(click())
        onData(`is`("Diesel")).inRoot(isPlatformPopup()).perform(click())
        onView(allOf(withId(R.id.cadastro_item_km_rodados), isDisplayed())).perform(
            click(),
            typeText("250"),
            closeSoftKeyboard()
        )
        onView(allOf(withId(R.id.cadastro_item_quantidade_litros), isDisplayed())).perform(
            click(),
            typeText("250"),
            closeSoftKeyboard()
        )
        onView(allOf(withId(R.id.cadastro_itens_botao_salvar), isDisplayed())).perform(click())
        pressBack()


    }



}
