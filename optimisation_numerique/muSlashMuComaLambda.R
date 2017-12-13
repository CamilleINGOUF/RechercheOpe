f <- function(x) {
  return(x[1]^2 + x[2]^2)
}

mumSlashMuComaLambda <- function(f,sigma,precision,lambda,mu) {
  m <- runif(n=2,min=-5,max=5)
  fitness <- f(m)
  
  w <- log(mu + 0.5) - log(1:mu)
  w <- w / sum(w)
  
  dyn <- c(fitness)
  
  while(abs(fitness) > precision) {
    xs <- array(dim = c(lambda, 2))
    fxs <- c()
    for(i in 1:lambda) {
      xprim <- m + rnorm(n = 2,mean = 0, sd=sigma)
      fx <- f(xprim)
      xs[i,] <- xprim
      fxs <- c(fxs, fx)
    }
    
    psuccess <- sum(fxs < fitness) / lambda
    if(psuccess > 0.2)
      sigma <- sigma * exp(1/3)
    else
      sigma <- sigma / exp(1/3)^1/4
    dyn <- c(dyn,fitness)
    
    orderFxs <- order(fxs)
    
    m <- c(0,0)
    for(j in 1:mu)
      m <- m + w[j] * xs[orderFxs[j],]
    
    fitness <- f(m)
  }
  
  return (list(x=m,dyn=dyn))
}

main <- function() {
  x <- mumSlashMuComaLambda(f=f,sigma=0.01,precision=10^-9,lambda=100,mu=10)
  cat(x$x," ",f(x$x))
  
  plot(x$dyn,type="l", log = "xy", ylim = c(10^-9,10), xlim = c(1,10^5))
}

main()