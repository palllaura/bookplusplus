import logo from "../assets/logo.png";
import '../App.css';

function Sidebar() {

    return (
        <div className="sidebar">
            <img src={logo} className="logo" alt="book++ logo"/>
            <h3>"It’s my story so clearly I’m the hero."</h3>
            <p>- Donatella</p>
        </div>
    );
}

export default Sidebar;
