# Author : SV
# date 2015/10/12

############################
# Comparaison d'algorithmes

# lit les donnÃ©es du HC best-improvement
bi <- read.table("bi.csv", header = TRUE, sep = " ")
# lit les donnÃ©es du HC first-improvement
fi <- read.table("fi.csv", header = TRUE, sep = " ")
# lit les donnÃ©es du HC first-improvement without replacement
fiwr <- read.table("fiwr.csv", header = TRUE, sep = " ")

# vÃ©rification des fichiers: affiche les premiÃ¨res lignes
head(bi)
head(fi)
head(fiwr)

# histogramme pour atteindre la fitness 30000 : nota, distribution unimodale
hist(bi[bi$fitmax == 30000,]$nbeval)
hist(fi[fi$fitmax == 30000,]$nbeval)
hist(fiwr[fiwr$fitmax == 30000,]$nbeval)

# calcul de la moyenne
mean(bi[bi$fitmax == 30000,]$nbeval)
mean(fi[fi$fitmax == 30000,]$nbeval)
mean(fiwr[fiwr$fitmax == 30000,]$nbeval)

# rÃ©sumÃ© des principales statistiques
summary(bi[bi$fitmax == 30000,]$nbeval)

# nuage de points du logarithme du nombre d'evaluation en fonction de la fitness a atteindre
plot(log10(nbeval) ~ fitmax, data = bi)
plot(log10(nbeval) ~ fitmax, data = fi)
plot(log10(nbeval) ~ fitmax, data = fiwr)

# Probleme : 
#    certain niveau de fitness ne sont pas atteint
#    il vaut mieux calculer l'Expected Running Time (ERT)

# calcul du success/echec
bi[["success"]] <- bi$fitmax < bi$fitness
fi[["success"]] <- fi$fitmax < fi$fitness
fiwr[["success"]] <- fiwr$fitmax < fiwr$fitness

# chargement de la librairie doBy
library(doBy)

# calcul du taux de success en fonction de la fitness a atteindre
bi.ps <- summaryBy(success+nbevalmax ~ fitmax, data = bi, FUN = mean)
fi.ps <- summaryBy(success+nbevalmax ~ fitmax, data = fi, FUN = mean)
fiwr.ps <- summaryBy(success+nbevalmax ~ fitmax, data = fiwr, FUN = mean)

# plots correspondants
plot(success.mean ~ fitmax, data = bi.ps)
points(success.mean ~ fitmax, data = fi.ps, col = "blue")
points(success.mean ~ fitmax, data = fiwr.ps, col = "red")

# Fonction permettant de calculer l'ERT
compute_ert <- function(df, df.ps) {
  df.esuccess <- summaryBy(nbeval ~ fitmax, data = df[df$success,], FUN = mean)
  df.ert <- merge(df.esuccess, df.ps, all = TRUE)
  df.ert <- replace(df.ert, is.na(df.ert), 0)
  u <- c()
  for(i in 1:nrow(df.ert)) {
    if (df.ert$success.mean[i] == 0) 
      u <- c(u, Inf)
    else
      u <- c(u, df.ert$nbeval.mean[i] + (1 - df.ert$success.mean[i]) / df.ert$success.mean[i] * df.ert$nbevalmax.mean[i])
  }
  df.ert[["ert"]] <- u
  return(df.ert)
}

bi.ert <- compute_ert(bi, bi.ps)
fi.ert <- compute_ert(fi, fi.ps)
fiwr.ert <- compute_ert(fiwr, fiwr.ps)

# tracer de l'ERT en fonction de la fitness a atteindre
plot(log10(ert) ~ fitmax, data = bi.ert[!is.infinite(bi.ert$ert), ], pch = 15)
points(log10(ert) ~ fitmax, data = fi.ert[!is.infinite(fi.ert$ert), ], pch = 16, col = "red")
points(log10(ert) ~ fitmax, data = fiwr.ert[!is.infinite(fiwr.ert$ert)], pch = 17, col = "blue")


#################################################
# Etude du paysage de fitness : marche adaptative

ma.bi <- read.table("bi-full.csv", header = TRUE, sep = " ")
ma.fiwr <- read.table("fiwr-full.csv", header = TRUE, sep = " ")

# distribution de fitness des optima locaux
hist(ma.bi$fitness)
hist(ma.fiwr$fitness)

# statistiques descriptives
summary(ma.bi$fitness)
summary(ma.fiwr$fitness)  # remarque : la qualitÃ© des optima locaux pour HC best-impr est meilleure

# longueur des marches adaptatives :
hist(ma.bi$nbstep)
hist(ma.fiwr$nbstep)

# statistiques descriptives
summary(ma.bi$nbstep)
summary(ma.fiwr$nbstep)  # remarque : les longueurs sont comparables

# Poursuite possible : comparer plusieurs instances du problÃ¨me du sac Ã  dos, ou comparer plusieurs fonctions de pÃ©nalitÃ©