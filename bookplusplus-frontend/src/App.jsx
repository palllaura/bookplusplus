import './App.css'
import Sidebar from "./components/Sidebar.jsx";
import Canvas from "./components/Canvas.jsx";

function App() {

  return (
      <div className="app-container">
          <Sidebar/>
          <div className="canvas-wrapper">
              <Canvas />
          </div>
      </div>
  )
}

export default App
