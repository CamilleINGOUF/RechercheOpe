f <- function(x) {
  return(x[1]^2 + x[2]^2)
}

f2 <- function(x) {
  return( (x[1]-1)^2 + (x[2]-1)^2 )
}

bif <- function(x) {
  return(c(f(x),f2(x)))
}

rnd_sol <- function(f,mu) {
  pop <- array(dim = c(mu,2))
  fpop <- array(dim = c(mu,2))
  
  for(i in 1:mu) {
    pop[i,] = runif(n = 2, min = -5, max = 5)
    fpop[i,] = f(pop[i,])
  }
  
  return (list(fitness_pop=fpop,pop=pop));
}

moea_d <- function(f,mu,sigma,n_eval_max) {
  lambda = array(dim = c(mu,2))
  for(i in 0:(mu-1)) {
    angle <- i*pi/((mu-1) * 2)
    lambda[i+1,] = c(cos(angle), sin(angle))
  }
  
  res <- rnd_sol(f=f,mu=mu)
  pop <- res$pop
  fpop <- res$fitness_pop
  n_eval <- mu
  
  while(n_eval < n_eval_max) {
    i = sample(1:mu,size = 1)
    
    if( i == 1) neighbor_dir <- c(1,2,3)
    else {
      if ( i == mu ) neighbor_dir <- c(mu-2, mu-1, mu)
      else neighbor_dir <- c(i-1, i, i+1)
    }
    x <- pop[sample(neighbor_dir,size = 1),]
    
    y <- (x + rnorm(n = 2,mean = 0, sd=sigma))
    fy <- f(y)
    
    n_eval = n_eval + 1
    
    for(j in neighbor_dir) {
      gj <- lambda[j,1] * fpop[j,1] + lambda[j,2] * fpop[j,2]
      gy <- lambda[j,1] * fy[1] + lambda[j,2] * fy[2]
      
      if(gy < gj) {
        pop[j,] <- y
        fpop [j,] <- fy
      }
    }
  }
  
  return (fpop)
}

main <- function() {
  res <- moea_d(f=bif,mu = 100,sigma=0.1,n_eval_max = 10000)
  plot(res,col="green",xlim = c(0,10), ylim = c(0,10))
}

main()