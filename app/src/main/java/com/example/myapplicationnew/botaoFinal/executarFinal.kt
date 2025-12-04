package com.example.myapplicationnew.botaoFinal

import android.accessibilityservice.AccessibilityService
import com.example.myapplicationnew.MyAccessibilityService

fun executarFinal(
    service: MyAccessibilityService,
    tempoInicial: Long
): Long {

    var t = tempoInicial

    // 25 - Finalizar gravação
    t += 1000
    service.postDelayed(t) { service.tap(410, 170) }

    // 26 - Limpar notificação
    t += 3000
    service.postDelayed(t) {
        service.swipe(
            startX = 200,
            startY = 306,
            endX = 600,
            endY = 306,
            duration = 300
        )
    }

    // 27 - Voltar
    t += 1200
    service.postDelayed(t) {
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
    }

    // 28 - 3 pontos novamente
    t += 1000
    service.postDelayed(t) { service.tap(772, 53) }

    // 29 - bloquear
    t += 1500
    service.postDelayed(t) { service.tap(532, 906) }

    // 30 - bloquear 2
    t += 1200
    service.postDelayed(t) { service.tap(580, 1137) }

    // 31 - voltar
    t += 1000
    service.postDelayed(t) {
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
    }

    // 32 - voltar novamente
    t += 1000
    service.postDelayed(t) {
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK)
    }

    // 33 - campo de pesquisa
    t += 1000
    service.postDelayed(t) { service.tap(169, 41) }

    // 34 - excluir pesquisa
    t += 800
    service.postDelayed(t) { service.tap(776, 144) }

    // 35 - abrir apps recentes
    t += 1000
    service.postDelayed(t) {
        service.performGlobalAction(AccessibilityService.GLOBAL_ACTION_RECENTS)
    }

    // 36 - arrastar app pra cima para fechar
    t += 1700
    service.postDelayed(t) {
        service.swipe(
            startX = 540,
            startY = 900,
            endX = 540,
            endY = 200,
            duration = 300
        )
    }

    // 36 - arrastar novamente app pra cima para fechar
    t += 1700
    service.postDelayed(t) {
        service.swipe(
            startX = 540,
            startY = 900,
            endX = 540,
            endY = 200,
            duration = 300
        )
    }

    // 37 - voltar para Home
    t += 1400
    service.postDelayed(t) {
        service.performGlobalAction(
            AccessibilityService.GLOBAL_ACTION_HOME
        )
    }

    // 38 - swipe tela Home (esquerda → direita)
    t += 1800
    service.postDelayed(t) {
        service.swipe(
            startX = 150,
            startY = 1000,
            endX = 900,
            endY = 1000,
            duration = 300
        )
    }

    return t
}
