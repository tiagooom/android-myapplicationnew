package com.example.myapplicationnew.botaoFinal

import android.accessibilityservice.AccessibilityService
import com.example.myapplicationnew.MyAccessibilityService

// Retorna o novo valor de t após executar tudo
fun executarPrimeiraParte(
    service: MyAccessibilityService,
    tInicial: Long
): Long {

    var t = tInicial

    // 0 - Ir para Home imediatamente
    service.performGlobalAction(
        AccessibilityService.GLOBAL_ACTION_HOME
    )

    // 0 - swipe tela Home (esquerda → direita)
    t += 1800
    service.postDelayed(t) {
        service.swipe(
            startX = 600,
            startY = 660,
            endX = 150,
            endY = 660,
            duration = 250
        )
    }

    // 0 - swipe tela Home (esquerda → direita)
    t += 1800
    service.postDelayed(t) {
        service.swipe(
            startX = 600,
            startY = 660,
            endX = 150,
            endY = 660,
            duration = 250
        )
    }

    // 1 - abrir app
    t += 1000
    service.postDelayed(t) { service.tap(705, 756) }

    // 2 - clicar na lupa
    t += 3000
    service.postDelayed(t) { service.tap(30, 516) }

    // 3 - campo de pesquisa
    t += 1000
    service.postDelayed(t) { service.tap(169, 41) }

    // 4 - nome
    t += 1000
    service.postDelayed(t) { service.tap(345, 155) }

    // 5 - contas
    t += 1000
    service.postDelayed(t) { service.tap(299, 99) }

    // 6 - foto
    t += 2000
    service.postDelayed(t) { service.tap(386, 159) }

    // 7 - 3 pontos
    t += 2000
    service.postDelayed(t) { service.tap(772, 53) }

    // 8 - desbloquear 1
    t += 1000
    service.postDelayed(t) { service.tap(522, 906) }

    // 9 - desbloquear 2
    t += 1000
    service.postDelayed(t) { service.tap(502, 672) }

    // 10 - confirmar desbloqueio
    t += 1000
    service.postDelayed(t) { service.tap(458, 682) }

    // 11 - voltar
    t += 1200
    service.postDelayed(t) {
        service.performGlobalAction(
            AccessibilityService.GLOBAL_ACTION_BACK
        )
    }

    // 12 - foto novamente
    t += 1200
    service.postDelayed(t) { service.tap(386, 159) }

    t += 1000
    // 14 - Abrir notificações (swipe topo → baixo)
    service.postDelayed(t) {
        service.swipe(
            startX = 540,
            startY = 10,
            endX = 540,
            endY = 1400,
            duration = 300
        )
    }

    // 15 - Ícone do gravador
    t += 1000
    service.postDelayed(t) { service.tap(410, 170) }

    // 16 - Botão "Áudio"
    t += 1000
    service.postDelayed(t) { service.tap(732, 212) }

    // 17 - Botão "Iniciar"
    t += 1000
    service.postDelayed(t) { service.tap(729, 378) }

    return t
}
