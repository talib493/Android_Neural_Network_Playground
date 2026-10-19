package com.example.neuralnetwork.ai

class Matrix(
    val value:Array<FloatArray>
){
    fun rows():Int{
        return value.size
    }
    fun columns():Int{
        return value[0].size
    }
    fun transpose():Array<FloatArray>{
        var transposed = Array(columns()) {
            FloatArray(rows())
        }
        for (i in 0 until rows()) {
            for (j in 0 until columns()) {
                transposed[j][i]=value[i][j]

            }
        }
        return transposed
    }
    fun matrixProduct(other: Matrix): Array<FloatArray> {
        require(this.columns()==other.rows()){
            "Matrix A Column no (${this.columns()}) should be equal to Matrix B rows No (${other.rows()})" }
        var a = Matrix(value)
        var answer = Array(a.rows()) { FloatArray(other.columns()) }
        for (i in 0 until a.rows()) {
            for (j in 0 until other.columns()) {
                var sum =0f
                for(k in 0 until a.columns()){
                    sum+= value[i][k]*other.value[k][j]
                }
                answer[i][j]=sum
            }
        }
        return answer
    }

    fun matrixVectorProduct(other: Vector): Vector {

        require(other.values.size == columns()) {
            "Vector size (${other.values.size}) must match Matrix columns (${columns()})"
        }

        val result = FloatArray(rows())

        for (i in 0 until rows()) {

            var sum = 0f

            for (j in 0 until columns()) {
                sum += value[i][j] * other.values[j]
            }

            result[i] = sum
        }

        return Vector(result)
    }
}