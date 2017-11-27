sa <- read.table("recuit.csv", header = TRUE, sep = " ")


ils <- read.table("ils.csv", header = TRUE, sep = " ")

sa1 <- sa[sa$nbeval == 100000,]
ils1 <- ils[ils$nbeval == 1000,]

summary(sa1$fitness)
summary(ils1$fitness)

boxplot(list(sa1$fitness,ils1$fitness))
