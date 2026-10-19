package com.example.neuralnetwork.ai

class Vector (val values:FloatArray){

    fun dot(other: Vector): Float{
        require(values.size == other.values.size) {
            "Vector sizes must match. Found ${values.size} and ${other.values.size}"
        }

        var sum = 0f
        for (i  in values.indices){
            sum += (values[i]*other.values[i])
        }
            return sum
    }


    fun add(other: Vector): Vector{
        require(values.size == other.values.size) {
            "Vector sizes must match. Found ${values.size} and ${other.values.size}"
        }
        val sum = FloatArray(values.size)

        for (i in values.indices){
            sum[i]=values[i]+other.values[i]

        }
        return Vector(sum)
    }


    fun subtract(other: Vector): Vector{
        require(values.size == other.values.size) {
            "Vector sizes must match. Found ${values.size} and ${other.values.size}"
        }
        val subtraction = FloatArray(values.size)

        for (i in values.indices){
            subtraction[i]=values[i]-other.values[i]

        }
        return Vector(subtraction)
    }

}
