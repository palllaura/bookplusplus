import logo from "../assets/logo.png";
import save from "../assets/savebutton.png"
import reload from "../assets/reload.png"
import edit from "../assets/edit.png"
import '../App.css';

function Sidebar({ onAddBook, onEdit, onSave, onReload }) {

    return (
        <div className="sidebar">
            <img
                src={logo}
                className="logo"
                alt="book++ logo"
                onClick={onAddBook}
            />
            <img src={edit}
                 className="button"
                 alt="edit button"
                 onClick={onEdit}
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
