import { Routes, Route } from "react-router-dom";
import UserDetail from "./pages/UserDetail";

function App() {
    return (
        <Routes>
            <Route path="/users/:usrNo" element={<UserDetail />} />
        </Routes>
    );
}

export default App;