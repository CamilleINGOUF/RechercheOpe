f <- function(x) {
  return((x[1] - 3)^2 + (x[2] - 1)^2)
}

gradientf <- function(x) {
  y <- c(2*(x[1] - 3), 2*(x[2] - 1))
  return(y)
}

descenteGradientPasFixe <- function(f, gradf, sigma, precision, x) {
  success <- FALSE
  dynamique <- c()
  while(!success) {
    w <- -(gradf(x))
    x <- x + sigma*w
    success <- abs(f(x)) < precision
    dynamique <- c(dynamique, f(x))
  }
  return (list("xmin"=(x),"dynamique"=(dynamique)))
}

main <- function() {
  x <- c(100,20)
  
  values <- descenteGradientPasFixe(f, gradientf, 0.1, 10^-7,x)
  cat(values$xmin,"\n",f(values$xmin),"\n")
  
  
  plot(values$dynamique)
}

main()