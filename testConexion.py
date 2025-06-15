import mysql.connector
from mysql.connector import Error

try:
    conn = mysql.connector.connect(
        host="sql8.freesqldatabase.com",
        user="sql8784857",
        password="rriLlQLmLj",
        database="sql8784857",
        port=3306
    )

    if conn.is_connected():
        print("✅ Connexion réussie à la base de données!")
        cursor = conn.cursor()
        cursor.execute("SELECT DATABASE();")
        db = cursor.fetchone()
        print(f"📂 Base de données actuelle: {db[0]}")
        cursor.close()
        conn.close()
    else:
        print("❌ Connexion échouée.")

except Error as e:
    print(f"❌ Erreur: {e}")
