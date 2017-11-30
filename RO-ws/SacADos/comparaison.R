sa <- read.table("sa.csv", header = TRUE, sep = " ")
ea <- read.table("ea.csv", header = TRUE, sep = " ")

summary(sa$fitness)
summary(ea$fitness)

boxplot(list(sa$fitness,ea$fitness))

plot(sa$fitness)
plot(ea$fitness)

hist(sa[sa$fitmax == 50000,]$fitness)
hist(ea[ea$fitmax == 50000,]$fitness)

plot(log10(fitness) ~ fitmax, data = sa)
plot(log10(fitness) ~ fitmax, data = ea)

sa.split <- split(sa$fitness, factor(sa$fitmax))
ea.split <- split(ea$fitness, factor(ea$nbeval))

boxplot(sa.split)
boxplot(ea.split)

