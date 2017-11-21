# Author : SV
# date 2015/10/12

############################
# recherche aléatoire

# lit les données des recherches aléatoires
df.rs <- read.table("rs.csv", header = TRUE, sep = " ")

# vérification du fichier: affiche les premières lignes
head(df.rs)

# histogramme pour un nombre d'évaluation 10000 : nota, la distribution n'est pas gaussienne
hist(df.rs[df.rs$nbeval == 10000,]$fitness)

# calcul de la moyenne
mean(df.rs[df.rs$nbeval == 10000,]$fitness)

# résumé des principales statistiques
summary(df.rs[df.rs$nbeval == 10000,]$fitness)

# nuage de points des fitness obtenues en fonction du nombre d'évaluation
plot(fitness ~ nbeval, data = df.rs)

# chargement de la librairie doBy
library(doBy)

# calcul des moyennes de fitness en fonction du nombre d'évaluation
df.rs.mean <- summaryBy(fitness ~ nbeval, data = df.rs, FUN = mean)

# plot correspondant
plot(fitness.mean ~ nbeval, data = df.rs.mean)

# grosso modo, on a l'impression que les données suivent une loi logarithmique
# d'ailleur, on peut tracer avec le log:
plot(fitness.mean ~ log10(nbeval), data = df.rs.mean)

# et même fitter un modèle linéaire
model <- lm(fitness.mean ~ log10(nbeval), data = df.rs.mean)

# trace la droite de régression
abline(model)
# donne le descriptif du modèle : noter un très bon coefficient R^2
summary(model)

## Remarque : sans la librairie doBy 

# split des données selon les valeur en ligne (obtient alors une liste)
l <- split(df.rs$fitness, factor(df.rs$nbeval))

# boite a moustache (boxplot) de la fitness en fonction du nombre d'évaluation (attention echelle non respectée en abscisse)
boxplot(l)

# calcul des moyennes (nota: R est un langage fonctionnel)
l.mean <- lapply(l, FUN = mean)

# et le plot fitness vs. moyenne
plot(names(l.mean), unlist(l.mean))

plot(names(l.mean), unlist(l.mean))

############################
# marche aléatoire

# lit les données de la marche aléatoire
df.rw <- read.table("rw.csv", header = TRUE, sep = " ")

# vérification du fichier: affiche les premières lignes
head(df.rw)

# histogramme des fitness : nota, la distribution n'est pas gaussienne
hist(df.rw$fitness)

# fitness en fonction du nombre d'itération. Nota : les variations sont "aléatoires" mais bornées.
plot(df.rw$fitness)

# calcul de la fonction d'autocorrelation : beaucoup de correlation entre les fitness des solutions voisines
cors <- acf(df.rw$fitness)

# le premier coefficient d'autocorrelation est très proche de 1:
print(cors[1])

