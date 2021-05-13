package com.kyh.musicplayer.data.`var`

class Code {
    enum class Genre constructor(
        private val value: String
    ){
        NOTHING("NOTHING"), ROCK_N_ROLL("ROCK_N_ROLL"), HIPHOP("HIPHOP"),
        EDM("EDM"), BALLADE("BALLADE"), CLASSIC("CLASSIC");

        override fun toString(): String {
            return this.value
        }
    }
}