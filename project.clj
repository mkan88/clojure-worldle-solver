(defproject wordle "0.1.0-SNAPSHOT"
  :description "Solving Wordle with Information Theory"
  :url "https://www.github.com/mkan88/wordle"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [clojure-csv "2.0.2"]]
  :repl-options {:init-ns wordle.core})
