package org.example.superid.core.resources

object AppStrings{
    const val TYPE_YOUR_EMAIL = "Email"
    const val TYPE_YOUR_PASSWORD = "Senha"
    const val CONFIRM_YOUR_PASSWORD = "Confirmar senha"
    const val PASSWORDS_MUST_MATCH = "As senhas precisam ser iguais e ter mais que 6 caracteres para criar sua conta."
    const val TYPE_YOUR_NAME = "Nome completo"
    const val LOGIN_ERROR = "Erro ao fazer login, confira seus dados"
    const val TITLE_ACTIVITY_PASSWORD_RESET = "PasswordReset"
    const val TITLE_ACTIVITY_CAM = "CamActivity"

    const val APP_DESCRIPTION = "Com nosso serviço, você poderá gerenciar suas senhas de forma simples e segura,\n" +
            "        além de contar com um sistema de autenticação rápido e prático para acessar sites parceiros.\n" +
            "        Basta deslizar para o lado e começar a usar nosso app com facilidade!"

    val TERMS_AND_CONDITIONS = """
        Termos e Condições de Uso do SuperID:

        Última atualização: 22/04/2025

        1. Aceitação dos Termos
        Ao utilizar o aplicativo SuperID, você concorda integralmente com estes Termos e Condições de Uso. Caso não concorde com algum dos termos, não será possível utilizar o aplicativo.

        2. Descrição do Serviço
        O SuperID é uma solução de autenticação digital que permite o login sem senha em aplicativos e serviços digitais, armazenando credenciais de forma segura.

        3. Cadastro e Autenticação
        Para utilizar o SuperID, é necessário criar uma conta fornecendo:
        - Nome completo
        - E-mail válido
        - Senha Mestre

        A autenticação é realizada por meio do serviço Firebase Authentication, da Google.

        4. Coleta e Armazenamento de Dados
        O SuperID coleta e armazena os seguintes dados:
        - UID (Identificador único de usuário do Firebase)
        - IMEI (Identificador do dispositivo)

        Esses dados são armazenados de forma segura no Firebase Firestore, garantindo a integridade e confidencialidade das informações.

        5. Privacidade e Proteção de Dados
        Comprometemo-nos a proteger sua privacidade em conformidade com a Lei Geral de Proteção de Dados (LGPD). Os dados coletados são utilizados exclusivamente para:
        - Garantir a segurança e funcionalidade do aplicativo
        - Melhorar a experiência do usuário
        - Cumprir obrigações legais e regulatórias

        Não compartilhamos suas informações com terceiros sem seu consentimento explícito.

        6. Direitos do Usuário
        Você tem o direito de:
        - Acessar quaisquer dados fornecidos por você, armazenados pelo aplicativo

        7. Responsabilidades do Usuário
        Você se compromete a:
        - Fornecer informações verídicas e atualizadas
        - Utilizar o aplicativo de forma ética e legal
        - O uso indevido do SuperID pode resultar na suspensão ou encerramento da conta.

        8. Modificações nos Termos
        Reservamo-nos o direito de alterar estes Termos e Condições de Uso a qualquer momento. As alterações serão comunicadas por meio do aplicativo ou por e-mail. O uso continuado do SuperID após as modificações constitui aceitação dos novos termos.

        9. Contato
        Em caso de dúvidas ou solicitações relacionadas a estes Termos e Condições de Uso, entre em contato conosco pelo e-mail: pi3superid@gmail.com
    """.trimIndent()
}
