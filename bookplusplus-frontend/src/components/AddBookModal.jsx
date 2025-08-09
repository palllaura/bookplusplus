import '../App.css';
import {useState} from "react";
import save from "../assets/savebutton.png";
import close from "../assets/close.png"

export default function AddBookModal({ onClose }) {
    const [state, setState] = useState({
        title: "",
        width: "",
        height: "",
        color: ""
    })

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
                        onChange={(e) => setState({ ...state, title: e.target.value })}
                    />
                </div>

                <div className="horizontal-input">
                    <div className="input-holder">
                        <p className="input-tooltip">Width:</p>
                        <input
                            type="number"
                            placeholder="..."
                            className="modal-input"
                            onChange={(e) => setState({ ...state, width: e.target.value })}
                        />
                    </div>

                    <div className="input-holder">
                        <p className="input-tooltip">Height:</p>
                        <input
                            type="number"
                            placeholder="..."
                            className="modal-input"
                            onChange={(e) => setState({ ...state, height: e.target.value })}
                        />
                    </div>
                </div>

                <div className="input-holder">
                    <p className="input-tooltip">Pick book color:</p>
                    <input
                        type="color"
                        className="color-picker"
                        onChange={(e) => setState({ ...state, color: e.target.value })}
                    />
                </div>

                <div className="modal-footer">
                    <img src={save}
                         className="button"
                         alt="save button"
                         onClick={onClose}
                    />
                </div>
            </div>
        </div>

    );
}
