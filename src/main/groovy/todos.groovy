import groovy.sql.GroovyRowResult
import groovy.sql.Sql

String username = "root"
String password = "root"

def sql = Sql.newInstance("jdbc:mysql://localhost:8889/practicegroovy", username,
        password)

// create schema
sql.execute('DROP TABLE IF EXISTS todos')
sql.execute '''
CREATE TABLE todos (
      id INT NOT NULL,
      todo VARCHAR(45) NOT NULL,
      completed BOOLEAN NULL,
      PRIMARY KEY (id)
  );
'''

// create some data
sql.execute '''
    INSERT INTO todos (id, todo, completed) VALUES (1, 'First Todo', false)
'''

def secondTodo = [id:2, todo: 'Second Todo', completed: true]

sql.execute """
  INSERT INTO todos (id, todo, completed)
  VALUES
  (${secondTodo.id}, ${secondTodo.todo}, ${secondTodo.completed})
"""


List<GroovyRowResult> rows = sql.rows("select * from todos")
println rows

sql.eachRow('select * from todos') { row ->
    println "Todo: @${row.todo}"
}


// calling close manually
sql.close()
println "Completed!"

