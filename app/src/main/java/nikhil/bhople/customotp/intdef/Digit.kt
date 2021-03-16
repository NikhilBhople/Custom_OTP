package nikhil.bhople.customotp.intdef

import androidx.annotation.IntDef

@IntDef(
    Digit.ZERO,
    Digit.ONE,
    Digit.TWO,
    Digit.THREE,
    Digit.FOUR,
    Digit.FIVE,
    Digit.SIX
)
@Retention(AnnotationRetention.SOURCE)
annotation class Digit {
    companion object {
        const val ZERO = 0
        const val ONE = 1
        const val TWO = 2
        const val THREE = 3
        const val FOUR = 4
        const val FIVE = 5
        const val SIX = 6
    }
}

