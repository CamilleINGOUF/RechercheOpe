#best-improvement
bi <- read.table("bi.csv", header = TRUE, sep = " ")
#first-improvement
fi <- read.table("fi.csv", header = TRUE, sep = " ")
#worst-improvement
wi <- read.table("wi.csv", header = TRUE, sep = " ")


head(bi)
head(fi)
head(wi)

hist(bi[bi$fitmax == 30000,]$fitness)
hist(fi[fi$fitmax == 30000,]$fitness)
hist(wi[wi$fitmax == 30000,]$fitness)

mean(bi[bi$fitmax == 30000,]$fitness)
mean(fi[fi$fitmax == 30000,]$fitness)
mean(wi[wi$fitmax == 30000,]$fitness)

summary(bi[bi$fitmax == 30000,]$fitness)
summary(fi[bi$fitmax == 30000,]$fitness)
summary(wi[bi$fitmax == 30000,]$fitness)

plot(log10(fitness) ~ fitmax, data = bi)
plot(log10(fitness) ~ fitmax, data = fi)
plot(log10(fitness) ~ fitmax, data = wi)

bi[["success"]] <- bi$fitmax < bi$fitness
fi[["success"]] <- fi$fitmax < fi$fitness
wi[["success"]] <- wi$fitmax < wi$fitness

plot(bi$fitness)
plot(fi$fitness)
plot(wi$fitness)
