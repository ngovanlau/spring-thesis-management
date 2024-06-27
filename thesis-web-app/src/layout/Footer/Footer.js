import "../Footer/Footer.css";

function Footer() {
	return (
		<div className="p-5 text-center bg-body-tertiary rounded-3 mt-auto">
			<h1 className="text-body-emphasis">
				Hệ thống quản lý khóa luận tốt nghiệp
			</h1>
			<p className="lead">
				Copyright &copy; 2024{" "}
				<b className="text-danger">Nguyễn Đức Mạnh, Ngô Văn Lâu</b>
			</p>
		</div>
	);
}

export default Footer;
