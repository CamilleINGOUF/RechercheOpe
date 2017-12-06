f <- function(x) {
  return(-x^3 + x^2 - 2*x + 5)
}

derf <- function(x) {
  return(-3*x^2+2*x-2)
}

f2 <- function(x) {
  return(10*x^4 - 20*x^3 - 90*x^2 + 20*x + 80)
}

derf2 <- function(x) {
  return(40*x^3 - 60*x^2 - 180*x + 20)
}

der2f2 <- function(x) {
  return(120*x^2 - 120*x - 180)
}

derivef <- function(f,h) {
  df <- function(x) {
    return((f(x+h) - f(x)) /h)
  }
  return (df)
}

g1 <- function(x) {
  return(cos(((x-0.5)^2 * (x + 1) + 1)/(x + 1)))
}

g2 <- function(x) {
  return (sqrt(exp((x - 0.25)^2)+1))
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
  newt <- newton(derf2, der2f2, 4, 10^-8)
  plot(newt$dyn)
  plot(seq(-100,100,by=0.5),f2(seq(-100,100,by=0.5)))
  
  dg1 <- derivef(g1,0.0001)
  ddg1 <- derivef(dg1,0.0001)
  x1 <- newton(dg1,ddg1,0,10^-6)
  
  dg2 <- derivef(g2,0.0001)
  ddg2 <- derivef(dg2,0.0001)
  x2 <- newton(dg2,ddg2,0,10^-6)
  
  cat(x1$x," ",x2$x," ",g1(x1$x)+g2(x2$x))
  plot(mesx, g1(mesx)+g2(mesx))
}