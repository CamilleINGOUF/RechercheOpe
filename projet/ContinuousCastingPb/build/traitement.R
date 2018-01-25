# lit les donnÃ©es du HC best-improvement
sa.dy <- read.table("sa_dynamique.csv", header = TRUE, sep = " ")
# lit les donnÃ©es du HC first-improvement
sa <- read.table("sa.csv", header = TRUE, sep = " ")

line(sa.dy$fitness, data = sa.dy$ite)