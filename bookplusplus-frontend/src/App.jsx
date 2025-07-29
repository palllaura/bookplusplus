import './App.css'
import Sidebar from "./components/Sidebar.jsx";
import Canvas from "./components/Canvas.jsx";
import {useEffect, useState} from "react";

function App() {
    const [books, setBooks] = useState([]);

    const fetchBooks = () => {
        return fetch('http://localhost:8080/api/allBooks')
            .then(res => res.json())
            .then(data => {
                setBooks(data);
                console.log('Fetched books:', data);
            });
    };

    useEffect(() => {
        fetchBooks();
    }, []);


  return (
      <div className="app-container">
          <Sidebar/>
          <div className="canvas-wrapper">
              <Canvas
                  books={books}
              />
          </div>
      </div>
  )
}

export default App
