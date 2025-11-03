import '../App.css';
import { useEffect, useRef, useState } from "react";
import save from "../assets/savebutton.png";
import close from "../assets/close.png";
import Canvas from "./BookPreviewCanvas.jsx";

export default function EditBookModal({ onSave, onClose, bookToEdit }) {
    const [title, setTitle] = useState('');
    const [pages, setPages] = useState('');
    const [height, setHeight] = useState('');
    const [fontsize, setFontsize] = useState('');
    const [color, setColor] = useState('');
    const [fontcolor, setFontcolor] = useState('');

    const canvasRef = useRef(null);

    useEffect(() => {
        if (bookToEdit) {
            setTitle(bookToEdit.title || '');
            setPages(bookToEdit.pages || '');
            setHeight(bookToEdit.height || '');
            setFontsize(bookToEdit.fontsize || '');
            setColor(bookToEdit.color || '#ffffff');
            setFontcolor(bookToEdit.fontcolor || '#000000');
        }
    }, [bookToEdit]);

    const handleSubmit = () => {
        const dto = createBookDto();
        onSave(dto);
        onClose();
    };

    function createBookDto() {
        return {
            id: bookToEdit?.id,
            title,
            pages,
            height,
            fontsize,
            color,
            fontcolor,
        };
    }

    if (!bookToEdit) {
        return null;
    }

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <div className="modal-header">
                    <h3>Edit book</h3>
                    <img
                        src={close}
                        className="button"
                        alt="close button"
                        onClick={onClose}
                    />
                </div>

                <div className="modal-layout">
                    <div className="input-fields">
                        <div className="input-holder">
                            <p className="input-tooltip">Book title:</p>
                            <input
                                type="text"
                                value={title}
                                placeholder="..."
                                className="modal-input"
                                onChange={e => setTitle(e.target.value)}
                            />
                        </div>

                        <div className="input-holder">
                            <p className="input-tooltip">Number of pages:</p>
                            <input
                                type="number"
                                value={pages}
                                placeholder="..."
                                className="modal-input"
                                onChange={e => setPages(e.target.value)}
                            />
                        </div>

                        <div className="input-holder">
                            <p className="input-tooltip">Height:</p>
                            <input
                                type="number"
                                value={height}
                                placeholder="..."
                                className="modal-input"
                                onChange={e => setHeight(e.target.value)}
                            />
                        </div>

                        <div className="input-holder">
                            <p className="input-tooltip">Font size:</p>
                            <input
                                type="number"
                                value={fontsize}
                                placeholder="..."
                                className="modal-input"
                                onChange={e => setFontsize(e.target.value)}
                            />
                        </div>

                        <div className="input-holder">
                            <p className="input-tooltip">Pick book color:</p>
                            <input
                                type="color"
                                value={color}
                                className="color-picker"
                                onChange={e => setColor(e.target.value)}
                            />
                        </div>

                        <div className="input-holder">
                            <p className="input-tooltip">Pick book title color:</p>
                            <input
                                type="color"
                                value={fontcolor}
                                className="color-picker"
                                onChange={e => setFontcolor(e.target.value)}
                            />
                        </div>
                    </div>

                    <div className="book-preview">
                        <Canvas
                            ref={canvasRef}
                            title={title}
                            pages={pages}
                            height={height}
                            fontsize={fontsize}
                            color={color}
                            fontcolor={fontcolor}
                        />
                    </div>
                </div>

                <div className="modal-footer">
                    <img
                        src={save}
                        className="button"
                        alt="save button"
                        onClick={handleSubmit}
                    />
                </div>
            </div>
        </div>
    );
}
