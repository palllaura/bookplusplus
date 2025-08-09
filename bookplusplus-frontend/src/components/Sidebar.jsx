import logo from "../assets/logo.png";
import save from "../assets/savebutton.png"
import '../App.css';

function Sidebar({ onAddBook, onSave }) {

    return (
        <div className="sidebar">
            <img
                src={logo}
                className="logo"
                alt="book++ logo"
                onClick={onAddBook}
            />
            <div>
                <h3>"It’s my story so clearly I’m the hero."</h3>
                <p>- Donatella</p>
            </div>
            <img src={save}
                 className="button"
                 alt="save button"
                 onClick={onSave}
            />
        </div>
    );
}

export default Sidebar;
