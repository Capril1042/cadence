interface MetricCardProps {
    title: string
    value: string
}

function MetricCard({ title, value }: MetricCardProps) {
    return (
        <div className="rounded-xl bg-white p-6 shadow-sm">
            <p className="text-sm text-slate-500">{title}</p>
            <p className="mt-2 text-3xl font-bold text-slate-900">
                {value}
            </p>
        </div>
    )
}

export default MetricCard