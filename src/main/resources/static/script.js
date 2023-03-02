use mediscreen
db.dropDatabase()

use mediscreen
db.createCollection("notes")

db.notes.insertMany([
  { _id: 1, patientId: 1, note: "Le patient déclare qu'il se sent très bien. Poids égal ou inférieur au poids recommandé", date: new Date("12/21/1986")},
  { _id: 2, patientId: 1, note: "Le patient déclare qu'il se sent fatigué pendant la journée. Il se plaint également de douleurs musculaires. Tests de laboratoire indiquant une microalbumine élevée" },
  { _id: 3, patientId: 1, note: "Le patient déclare qu'il ne se sent pas si fatigué que ça. Fumeur, il a arrêté dans les 12 mois précédents. Tests de laboratoire indiquant que les anticorps sont élevés" },
  { _id: 4, patientId: 2, note: "Le patient déclare qu'il ressent beaucoup de stress au travail. Il se plaint également que son audition est anormale dernièrement" },
  { _id: 5, patientId: 2, note: "Le patient déclare avoir fait une réaction aux médicaments au cours des 3 derniers mois. Il remarque également que son audition continue d'être anormale" },
  { _id: 6, patientId: 2, note: "Tests de laboratoire indiquant une microalbumine élevée" },
  { _id: 7, patientId: 2, note: "Le patient déclare que tout semble aller bien. Le laboratoire rapporte que l'hémoglobine A1C dépasse le niveau recommandé. Le patient déclare qu’il fume depuis longtemps" },
  { _id: 8, patientId: 3, note: "Le patient déclare qu'il fume depuis peu" },
  { _id: 9, patientId: 3, note: "Tests de laboratoire indiquant une microalbumine élevée" },
  { _id: 10, patientId: 3, note: "Le patient déclare qu'il est fumeur et qu'il a cessé de fumer l'année dernière. Il se plaint également de crises d’apnée respiratoire anormales. Tests de laboratoire indiquant un taux de cholestérol LDL élevé" },
  { _id: 11, patientId: 3, note: "Tests de laboratoire indiquant un taux de cholestérol LDL élevé" },
  { _id: 12, patientId: 4, note: "Le patient déclare qu'il lui est devenu difficile de monter les escaliers. Il se plaint également d’être essoufflé. Tests de laboratoire indiquant que les anticorps sont élevés. Réaction aux médicaments"},
  { _id: 13, patientId: 4, note: "Le patient déclare qu'il a mal au dos lorsqu'il reste assis pendant longtemps"},
  { _id: 14, patientId: 4, note: "Le patient déclare avoir commencé à fumer depuis peu. Hémoglobine A1C supérieure au niveau recommandé"},
  { _id: 15, patientId: 5, note: "Le patient déclare avoir des douleurs au cou occasionnellement. Le patient remarque également que certains aliments ont un goût différent. Réaction apparente aux médicaments. Poids corporel supérieur au poids recommandé"},
  { _id: 16, patientId: 5, note: "Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite. Taille incluse dans la fourchette concernée"},
  { _id: 17, patientId: 5, note: "Le patient déclare qu'il souffre encore de douleurs cervicales occasionnelles. Tests de laboratoire indiquant une microalbumine élevée. Fumeur, il a arrêté dans les 12 mois précédents"},
  { _id: 18, patientId: 5, note: "Le patient déclare avoir eu plusieurs épisodes de vertige depuis la dernière visite. Tests de laboratoire indiquant que les anticorps sont élevés"},
  { _id: 19, patientId: 6, note: "Le patient déclare qu'il se sent bien. Poids corporel supérieur au poids recommandé"},
  { _id: 20, patientId: 6, note: "Le patient déclare qu'il se sent bien"},
  { _id: 21, patientId: 7, note: "Le patient déclare qu'il se réveille souvent avec une raideur articulaire. Il se plaint également de difficultés pour s’endormir. Poids corporel supérieur au poids recommandé. Tests de laboratoire indiquant un taux de cholestérol LDL élevé"},
  { _id: 22, patientId: 8, note: "Les tests de laboratoire indiquent que les anticorps sont élevés. Hémoglobine A1C supérieure au niveau recommandé"},
  { _id: 23, patientId: 9, note: "Le patient déclare avoir de la difficulté à se concentrer sur ses devoirs scolaires. Hémoglobine A1C supérieure au niveau recommandé"},
  { _id: 24, patientId: 9, note: "Le patient déclare qu'il s’impatiente facilement en cas d’attente prolongée. Il signale également que les produits du distributeur automatique ne sont pas bons. Tests de laboratoire signalant des taux anormaux de cellules sanguines"},
  { _id: 25, patientId: 9, note: "Le patient signale qu'il est facilement irrité par des broutilles. Il déclare également que l'aspirateur des voisins fait trop de bruit. Tests de laboratoire indiquant que les anticorps sont élevés"},
  { _id: 26, patientId: 10, note: "Le patient déclare qu'il n'a aucun problème"},
  { _id: 27, patientId: 10, note: "Le patient déclare qu'il n'a aucun problème. Taille incluse dans la fourchette concernée. Hémoglobine A1C supérieure au niveau recommandé"},
  { _id: 28, patientId: 10, note: "Le patient déclare qu'il n'a aucun problème. Poids corporel supérieur au poids recommandé. Le patient a signalé plusieurs épisodes de vertige depuis sa dernière visite"},
  { _id: 29, patientId: 10, note: "Le patient déclare qu'il n'a aucun problème. Tests de laboratoire indiquant une microalbumine élevée"}
])