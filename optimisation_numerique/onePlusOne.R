

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

onePlusOneESFifthRule <- function(f,sigma,precision) {
  m <- runif(n=2,min=-5,max=5)
  dyn <- c()
  fitness <- f(m)
  sig <- sigma
  while(abs(fitness) > precision){
    xprime <- rnorm(n=2,mean=m,sd=sig)
    fxprime <- f(xprime)
    if(fitness > fxprime) {
      m <- xprime
      fitness <- fxprime
      sig <- sig * exp(1/3)
    } else {
      sig <- sig / (exp(1/3)^(1/4))
    }
    dyn <- c(dyn,f(m))
  }
  return (list(x=m,dyn=dyn));
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

execOnePlusOne <- function() {
  x <- onePlusOneES(f=f,sigma=10,precision=10^-2)
  x1 <- onePlusOneES(f=f,sigma=1,precision=10^-4)
  x2 <- onePlusOneES(f=f,sigma=0.1,precision=10^-6)
  x3 <- onePlusOneES(f=f,sigma=0.01,precision=10^-6)
  x4 <- onePlusOneES(f=f,sigma=0.001,precision=10^-6)
  cat(x1$x," ",f(x1$x))
  plot(x2$dyn,type="l", ylim = c(10^-8,10), log = "xy")
  lines(x1$dyn,col="green")
  lines(x$dyn,col="blue")
  lines(x3$dyn,col="pink")
  lines(x4$dyn,col="red")
}

execOnePlusOneFifthRule <- function() {
  x <- onePlusOneESFifthRule(f=f,sigma=10,precision=10^-10)
  x1 <- onePlusOneESFifthRule(f=f,sigma=1,precision=10^-10)
  x2 <- onePlusOneESFifthRule(f=f,sigma=0.1,precision=10^-10)
  x3 <- onePlusOneESFifthRule(f=f,sigma=0.01,precision=10^-10)
  x4 <- onePlusOneESFifthRule(f=f,sigma=0.001,precision=10^-10)
  cat(x1$x," ",f(x1$x))
  plot(x2$dyn,type="l", ylim = c(10^-8,10), log = "xy")
  lines(x1$dyn,col="green")
  lines(x$dyn,col="blue")
  lines(x3$dyn,col="pink")
  lines(x4$dyn,col="red")
}

main <- function() {
  execOnePlusOneFifthRule()
}

main()