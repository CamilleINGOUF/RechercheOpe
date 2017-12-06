f <- function(x) {
  return(-x^3 + x^2 - 2*x + 5)
}

derf <- function(x) {
  return(-3*x^2+2*x-2)
}

newton <- function(f, derf, x, precision) {
  success <- FALSE
  dyn <- c()
  while(!success) {
    x <- x - (f(x) / derf(x))
    dyn <- c(dyn, f(x))
    success <- abs(f(x)) < precision
  }
  return (list(x=(x),dyn=dyn))
}

main <- function() {
  newt <- newton(f, derf, 10, 10^-2)
  cat(newt$x,"\n",f(newt$x))
  plot(newt$dyn)
}