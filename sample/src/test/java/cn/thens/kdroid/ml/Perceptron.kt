package cn.thens.kdroid.ml

/**
 * 感知器
 */

import cn.thens.kdroid.ml.util.ActivationFunction.step
import cn.thens.kdroid.ml.util.GaussianDistribution
import org.junit.Test
import java.util.*


/**
 * @param inputCount dimensions of input data
 */
@SuppressWarnings("Typo")
class Perceptron(private var inputCount: Int) {
    var weight: DoubleArray = DoubleArray(inputCount)  // weight vector of perceptrons

    fun train(input: DoubleArray, output: Int, learningRate: Double): Int {

        var classified = 0
        val c = (0 until inputCount).sumByDouble { weight[it] * input[it] * output.toDouble() }

        // check if the data is classified correctly

        // apply steepest descent method if the data is wrongly classified
        if (c > 0) {
            classified = 1
        } else {
            for (i in 0 until inputCount) {
                weight[i] += learningRate * input[i] * output.toDouble()
            }
        }

        return classified
    }

    fun predict(x: DoubleArray): Int {
        val preActivation = (0 until inputCount).sumByDouble { weight[it] * x[it] }
        return step(preActivation)
    }

    class YaTest {
        @Test fun test() {
            main(arrayOf(""))
        }
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            //
            // Declare (Prepare) variables and constants for perceptrons
            //
            val trainCount = 1000  // number of training data
            val testCount = 200   // number of test data
            val inputCount = 2        // dimensions of input data

            val trainInputData = Array(trainCount) { DoubleArray(inputCount) }  // input data for training
            val trainOutputData = IntArray(trainCount)               // output data (label) for training

            val testInputData = Array(testCount) { DoubleArray(inputCount) }  // input data for test
            val testOutputData = IntArray(testCount)               // label of inputs
            val predictedData = IntArray(testCount)          // output data predicted by the model

            val epochs = 2000   // maximum training epochs
            val learningRate = 1.0  // learning rate can be 1 in perceptrons


            //
            // Create training data and test data for demo.
            //
            // Let training data set for each class follow Normal (Gaussian) distribution here:
            //   class 1 : x1 ~ N( -2.0, 1.0 ), y1 ~ N( +2.0, 1.0 )
            //   class 2 : x2 ~ N( +2.0, 1.0 ), y2 ~ N( -2.0, 1.0 )
            //

            val random = Random(1234)  // seed random
            val g1 = GaussianDistribution(-2.0, 1.0, random)
            val g2 = GaussianDistribution(2.0, 1.0, random)

            // data set in class 1
            for (i in 0 until trainCount / 2 - 1) {
                trainInputData[i][0] = g1.random()
                trainInputData[i][1] = g2.random()
                trainOutputData[i] = 1
            }
            for (i in 0 until testCount / 2 - 1) {
                testInputData[i][0] = g1.random()
                testInputData[i][1] = g2.random()
                testOutputData[i] = 1
            }

            // data set in class 2
            for (i in trainCount / 2 until trainCount) {
                trainInputData[i][0] = g2.random()
                trainInputData[i][1] = g1.random()
                trainOutputData[i] = -1
            }
            for (i in testCount / 2 until testCount) {
                testInputData[i][0] = g2.random()
                testInputData[i][1] = g1.random()
                testOutputData[i] = -1
            }


            //
            // Build SingleLayerNeuralNetworks model
            //

            var epoch = 0  // training epochs

            // construct perceptrons
            val classifier = Perceptron(inputCount)

            // train models
            while (true) {
                var classified_ = 0

                for (i in 0 until trainCount) {
                    classified_ += classifier.train(trainInputData[i], trainOutputData[i], learningRate)
                }

                epoch++

                if (classified_ == trainCount || epoch > epochs) break  // when all data classified correctly
            }


            // test
            for (i in 0 until testCount) {
                predictedData[i] = classifier.predict(testInputData[i])
            }


            //
            // Evaluate the model
            //

            val confusionMatrix = Array(2) { IntArray(2) }
            var accuracy = 0.0
            var precision = 0.0
            var recall = 0.0

            for (i in 0 until testCount) {

                if (predictedData[i] > 0) {
                    if (testOutputData[i] > 0) {
                        accuracy += 1.0
                        precision += 1.0
                        recall += 1.0
                        confusionMatrix[0][0] += 1
                    } else {
                        confusionMatrix[1][0] += 1
                    }
                } else {
                    if (testOutputData[i] > 0) {
                        confusionMatrix[0][1] += 1
                    } else {
                        accuracy += 1.0
                        confusionMatrix[1][1] += 1
                    }
                }

            }

            accuracy /= testCount.toDouble()
            precision /= (confusionMatrix[0][0] + confusionMatrix[1][0]).toDouble()
            recall /= (confusionMatrix[0][0] + confusionMatrix[0][1]).toDouble()

            println("----------------------------")
            println("Perceptrons model evaluation")
            println("----------------------------")
            System.out.printf("Accuracy:  %.1f %%\n", accuracy * 100)
            System.out.printf("Precision: %.1f %%\n", precision * 100)
            System.out.printf("Recall:    %.1f %%\n", recall * 100)

        }
    }
}