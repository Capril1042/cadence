function Header() {
    return (
        <header className="border-b border-slate-200 bg-white">
            <div className="mx-auto flex max-w-7xl items-center justify-between px-8 py-5">
                <h1 className="text-2xl font-bold text-slate-900">
                    cadence
                </h1>

                <nav className="flex gap-8 text-sm font-medium text-slate-600">
                    <a href="#" className="text-indigo-600">Dashboard</a>
                    <a href="#">Classes</a>
                    <a href="#">Members</a>
                    <a href="#">Analytics</a>
                </nav>
            </div>
        </header>
    )
}

export default Header