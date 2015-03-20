gbinCat
=======

Concaténe un ensemble de fichiers gbin dans un fichier CSV stocké dans HDFS.

## Exemples d'utilisation
```
$ HADOOP_CLASSPATH=target/gbincat-1.0-SNAPSHOT-jar-with-dependencies.jar \
  hadoop adam.gaia.gbincat.GbinCat \
  -d data/RDS-14-A-Converted-0.01/ -o RDS-14.csv -gog -n 10
```
Traite l'ensemble des fichiers gbin des sous-répertoires de data/RDS-14-A-Converted-0.01/ (-d) et
produit le fichier RDS-14.csv (-o) dans HDFS.
Les fichiers doivent être au format GOG (-gog).
Seuls les 10 premiers objets sont traités.

```
$ HADOOP_CLASSPATH=target/gbincat-1.0-SNAPSHOT-jar-with-dependencies.jar \
  hadoop adam.gaia.gbincat.GbinCat \
  -d data/igsl_source -o igsl_source.csv -igsl -n 10
```
Idem pour le type IGSL
