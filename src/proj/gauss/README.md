# Gauss-Newton
The code that actually performs the operations all lies in the GaussNewton class.
There are two methods. The performOriginalGaussNewton method uses an inverse to
complete the operation, whereas the performModifiedGaussNewton method uses Givens
rotations to factor the matrix into QR and complete the operation.

To run the individual functions, gn_qua, gn_log, gn_rat, and gn_exp, you simply
run the main method in the corresponding class. The class's main method will call
the corresponding function. **I.e. ExponentialGaussNewton.main() will call gn_exp**.

* ExponentialGaussNewton = gn_exp
* QuadraticGaussNewton = gn_qua
* LogarithmicGaussNewton = gn_log
* RationalGaussNewton = gn_rat

Once the main method is run, the UserInteraction class will ask for user input
and will continue asking until the user enters valid data. The UserInteraction
class will then parse the data into a format usable by the Gauss-Newton methods.

The Gauss-Newton method will then be run and the result will be output to the console.