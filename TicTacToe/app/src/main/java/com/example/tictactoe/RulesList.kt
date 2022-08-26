package com.example.tictactoe

object RulesList {
    val RulesList = listOf<Rules>(
        Rules("1.) Have the first player go first. \nfirst player can decide whether to go with X or O."
            ,R.drawable.step1),
        Rules("2.) Have the second player go second. \nThe second player should put symbol apart from the one taken."
            ,R.drawable.step2),
        Rules("3.) Keep alternating moves until one of the players has drawn a row || col || diagonal of three symbols."
            ,R.drawable.step3),
        Rules("4.) Row or Column of 3 Xs || Os\n (O won)",R.drawable.step4),
        Rules("5.) Diagonal of 3 Xs || Os\n" +
                " (O won)",R.drawable.step5)
    )
}