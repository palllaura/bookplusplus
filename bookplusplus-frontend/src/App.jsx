import './App.css'
import Sidebar from "./components/Sidebar.jsx";
import Canvas from "./components/Canvas.jsx";
import {fetchBooks, saveBookLocations} from './services/bookService';
import {useEffect, useRef, useState} from "react";
import AddBookModal from "./components/AddBookModal.jsx";

function App() {
    const [books, setBooks] = useState([]);
    const canvasRef = useRef(null);
    const [isModalOpen, setIsModalOpen] = useState(false);

    useEffect(() => {
        async function loadBooks() {
            try {
                const data = await fetchBooks();
                setBooks(data);
                console.log('Fetched books:', data);
            } catch (error) {
                console.error('Failed to fetch books:', error);
            }
        }
        loadBooks();
    }, []);

    const handleSave = async () => {
        if (!canvasRef.current) return;

        const bookLocations = canvasRef.current.getCurrentBookLocations();

        try {
            await saveBookLocations(bookLocations);
            console.log('Book locations saved successfully!');
        } catch (error) {
            console.error('Failed to save book locations:', error);
        }
    };

    return (
        <div className="app-container">
            <Sidebar
                onSave={handleSave}
                onAddBook={() => setIsModalOpen(true)}
            />
            <div className="canvas-wrapper">
                <Canvas ref={canvasRef} books={books} />
            </div>
            {isModalOpen && (<AddBookModal
                onClose={() => setIsModalOpen(false)}
            />)}
        </div>
    )
}

export default App
