import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

function UserDetail() {
    const { usrNo } = useParams();
    const [user, setUser] = useState(null);

    useEffect(() => {
        axios.get(`/api/users/getUser/${usrNo}`)
            .then(res => setUser(res.data))
            .catch(err => console.error(err));
    }, [usrNo]);

    if (!user) return <div>Loading...</div>;

    return (
        <div className="container mt-4">
            <h3>사용자 정보</h3>
            <p>번호: {user.usrNo}</p>
            <p>ID: {user.usrId}</p>
            <p>이름: {user.usrNm}</p>
        </div>
    );
}

export default UserDetail;
