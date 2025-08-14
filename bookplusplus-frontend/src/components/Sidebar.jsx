import logo from "../assets/logo.png";
import save from "../assets/savebutton.png"
import reload from "../assets/reload.png"
import '../App.css';

function Sidebar({ onAddBook, onSave, onReload }) {

    return (
        <div className="sidebar">
            <img
                src={logo}
                className="logo"
                alt="book++ logo"
                onClick={onAddBook}
            />
            <img src={save}
                 className="button"
                 alt="save button"
                 onClick={onSave}
            />
            <img
                src={reload}
                className="button"
                alt="reload page"
                onClick={onReload}
            />
        </div>
    );
}

export default Sidebar;
