

normal <- function() {
  x <- seq(-5,5, by = 0.01)
  plot(x, dnorm(x, mean = 0, sd = 1), type = "l")
  lines(x, dnorm(x, mean = 3, sd = 1), col = "pink")
  lines(x, dnorm(x, mean = -3, sd = 1), col = "purple")
  lines(x, dnorm(x, mean = 0, sd = 2), col = "blue")
  lines(x, dnorm(x, mean = 0, sd = 0.5), col = "green")
  cat(rnorm(n=10,mean=0,sd=1))
  
}

genereNormal <- function(x,sig1,sig2) {
  x1 <- rnorm(n=500,mean=x,sd=sig1)
  x2 <- rnorm(n=500,mean=x,sd=sig2)
  return (list(x1=x1,x2=x2))
}

genereNormalAngle <- function(x,sig1,sig2,theta) {
  x1 <- rnorm(n=500,mean=x,sd=sig1)
  x2 <- rnorm(n=500,mean=x,sd=sig2)
  
  X1 <- cos(theta) * x1+ sin(theta) * x2
  X2 <- -sin(theta) * x1 + cos(theta) * x2
  return (list(x1=X1,x2=X2))
}

plotNormal <- function() {
  x0 <- genereNormal(0,5,5)
  plot(x0$x1,x0$x2)
  
  x1 <- genereNormal(0,1,1)
  points(x1$x1,x1$x2, col="magenta")
  
  x2 <- genereNormal(0,1,5)
  points(x2$x1,x2$x2, col="green")
  
  x3 <- genereNormalAngle(0,1,5,pi/3)
  points(x3$x1,x3$x2, col="blue")
}

f <- function(x) {
  return(x[1]^2 + x[2]^2)
}

onePlusOneES <- function(f,sigma,precision) {
  m <- runif(n=2,min=-5,max=5)
  dyn <- c()
  fitness <- f(m)
  while(abs(fitness) > precision){
    xprime <- rnorm(n=2,mean=m,sd=sigma)
    fxprime <- f(xprime)
    if(fitness > fxprime) {
      m <- xprime
      fitness <- fxprime
    }
    dyn <- c(dyn,f(m))
  }
  return (list(x=m,dyn=dyn));
}

main <- function() {
  #plotNormal()
  x <- onePlusOneES(f=f,sigma=1,precision=10^-3)
  cat(x$x," ",f(x$x))
  plot(log(x$dyn))
}

main()