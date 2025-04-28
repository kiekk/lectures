import http from 'k6/http';

export default function () {
    http.get('http://{EC2 IP 주소}:8080/boards/redis');
}