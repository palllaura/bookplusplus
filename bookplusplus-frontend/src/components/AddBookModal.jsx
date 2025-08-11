import '../App.css';
import {useState} from "react";
import save from "../assets/savebutton.png";
import close from "../assets/close.png"

export default function AddBookModal({ onSave, onClose }) {
    const [title, setTitle] = useState("");
    const [width, setWidth] = useState("");
    const [height, setHeight] = useState("");
    const [color, setColor] = useState("");

    const handleSubmit = () => {
        const dto = createBookDto();
        onSave(dto);
        onClose();
    }

    function createBookDto() {
        return {title: title,  width: width, height: height, color: color};
    }

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <div className="modal-header">
                    <h3>Add new book</h3>
                    <img src={close}
                         className="button"
                         alt="close button"
                         onClick={onClose}
                    />
                </div>
                <div className="input-holder">
                    <p className="input-tooltip">Book title:</p>
                    <input
                        type="text"
                        placeholder="..."
                        className="modal-input"
                        onChange={e => setTitle(e.target.value)}
                    />
                </div>

                <div className="horizontal-input">
                    <div className="input-holder">
                        <p className="input-tooltip">Width:</p>
                        <input
                            type="number"
                            placeholder="..."
                            className="modal-input"
                            onChange={e => setWidth(e.target.value)}
                        />
                    </div>

                    <div className="input-holder">
                        <p className="input-tooltip">Height:</p>
                        <input
                            type="number"
                            placeholder="..."
                            className="modal-input"
                            onChange={e => setHeight(e.target.value)}
                        />
                    </div>
                </div>

                <div className="input-holder">
                    <p className="input-tooltip">Pick book color:</p>
                    <input
                        type="color"
                        className="color-picker"
                        onChange={e => setColor(e.target.value)}
                    />
                </div>

                <div className="modal-footer">
                    <img src={save}
                         className="button"
                         alt="save button"
                         onClick={handleSubmit}
                    />
                </div>
            </div>
        </div>

    );
}
