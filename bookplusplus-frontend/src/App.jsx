import './App.css'
import Sidebar from "./components/Sidebar.jsx";
import Canvas from "./components/Canvas.jsx";
import {addBook, fetchBookById, fetchBooks, saveBookLocations} from './services/bookService';
import {useEffect, useRef, useState} from "react";
import AddBookModal from "./components/AddBookModal.jsx";
import EditBookModal from "./components/EditBookModal.jsx";

function App() {
    const [books, setBooks] = useState([]);
    const [bookToEdit, setBookToEdit] = useState();
    const canvasRef = useRef(null);
    const [isAddBookModalOpen, setIsAddBookModalOpen] = useState(false);
    const [isEditBookModalOpen, setIsEditBookModalOpen] = useState(false);

    const loadBooks = async () => {
        try {
            const data = await fetchBooks();
            setBooks(data);
            console.log('Fetched books:', data);
        } catch (error) {
            console.error('Failed to fetch books:', error);
        }
    };

    const getBookToEdit = async (id) => {
        try {
            const book = await fetchBookById(id);
            setBookToEdit(book);
        } catch (error) {
            console.error('Failed to fetch book: ', error);
            alert('Failed to fetch book')
        }
    }

    useEffect(() => {
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

    const handleReload = async () => {
        await loadBooks();
        await canvasRef.current.reloadBooks(books);
    }


    const handleAddBook = async (dto) => {
        try {
            await addBook(dto);
            console.log('Book added successfully');
            await loadBooks();
        } catch (error) {
            console.error('Failed to save book: ', error);
            alert('Failed to save book')
        }
    }

    return (
        <div className="app-container">
            <Sidebar
                onSave={handleSave}
                onAddBook={() => setIsAddBookModalOpen(true)}
                onReload={handleReload}
            />
            <div className="canvas-wrapper">
                <Canvas
                    ref={canvasRef}
                    books={books}
                    onEdit={async (id) => {
                        await getBookToEdit(id);
                        setIsEditBookModalOpen(true);
                    }}
                />
            </div>
            {isAddBookModalOpen && (<AddBookModal
                onSave={handleAddBook}
                onClose={() => setIsAddBookModalOpen(false)}
            />
            )}

            {isEditBookModalOpen && (
                <EditBookModal
                    onClose={() => setIsEditBookModalOpen(false)}
                    bookToEdit={bookToEdit}
                />
            )}

        </div>
    )
}

export default App
