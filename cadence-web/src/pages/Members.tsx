import { useEffect, useState } from 'react'
import { addMember, getAllMembers } from '../services/memberService'
import type { CreateMemberInput } from '../services/memberService'
import type { Member } from '../types/Member'
import Header from '../components/layout/Header'

function Members() {
    const [members, setMembers] = useState<Member[]>([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState('')

    const [form, setForm] = useState<CreateMemberInput>({
        firstName: '',
        lastName: '',
        email: '',
        membershipType: 'Monthly',
    })

    useEffect(() => {
        async function loadMembers() {
            try {
                const data = await getAllMembers()
                setMembers(data)
            } catch {
                setError('Unable to load members.')
            } finally {
                setLoading(false)
            }
        }

        loadMembers()
    }, [])

    function handleChange(
        event: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
    ) {
        const { name, value } = event.target

        setForm((currentForm) => ({
            ...currentForm,
            [name]: value,
        }))
    }

    async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
        event.preventDefault()
        setError('')

        try {
            const newMember = await addMember(form)
            setMembers((currentMembers) => [...currentMembers, newMember])

            setForm({
                firstName: '',
                lastName: '',
                email: '',
                membershipType: 'Monthly',
            })
        } catch {
            setError('Unable to add member. Please try again.')
        }
    }

    return (
        <>
            <Header />
            <main className="min-h-screen bg-gray-50 p-8">
            <div className="mx-auto max-w-6xl">
                <h1 className="mb-2 text-3xl font-bold text-gray-900">
                    Members
                </h1>
                <p className="mb-8 text-gray-500">
                    Manage your fitness studio members.
                </p>

                <section className="mb-8 rounded-xl bg-white p-6 shadow-sm">
                    <h2 className="mb-5 text-xl font-semibold text-gray-900">
                        Add New Member
                    </h2>

                    <form onSubmit={handleSubmit} className="grid gap-4 md:grid-cols-2">
                        <input
                            name="firstName"
                            value={form.firstName}
                            onChange={handleChange}
                            placeholder="First name"
                            required
                            className="rounded-lg border border-gray-300 p-3"
                        />

                        <input
                            name="lastName"
                            value={form.lastName}
                            onChange={handleChange}
                            placeholder="Last name"
                            required
                            className="rounded-lg border border-gray-300 p-3"
                        />

                        <input
                            name="email"
                            type="email"
                            value={form.email}
                            onChange={handleChange}
                            placeholder="Email address"
                            required
                            className="rounded-lg border border-gray-300 p-3"
                        />

                        <select
                            name="membershipType"
                            value={form.membershipType}
                            onChange={handleChange}
                            className="rounded-lg border border-gray-300 p-3"
                        >
                            <option value="Monthly">Monthly</option>
                            <option value="Annual">Annual</option>
                        </select>

                        <button
                            type="submit"
                            className="rounded-lg bg-indigo-600 px-5 py-3 font-medium text-white hover:bg-indigo-700 md:col-span-2"
                        >
                            Add Member
                        </button>
                    </form>
                </section>

                {error && (
                    <p className="mb-4 text-red-600">{error}</p>
                )}

                <section className="rounded-xl bg-white p-6 shadow-sm">
                    <h2 className="mb-5 text-xl font-semibold text-gray-900">
                        All Members
                    </h2>

                    {loading ? (
                        <p className="text-gray-500">Loading members...</p>
                    ) : members.length === 0 ? (
                        <p className="text-gray-500">No members found.</p>
                    ) : (
                        <div className="overflow-x-auto">
                            <table className="w-full text-left">
                                <thead>
                                <tr className="border-b text-sm text-gray-500">
                                    <th className="p-3">Name</th>
                                    <th className="p-3">Email</th>
                                    <th className="p-3">Membership</th>
                                    <th className="p-3">Status</th>
                                </tr>
                                </thead>
                                <tbody>
                                {members.map((member) => (
                                    <tr key={member.id} className="border-b last:border-0">
                                        <td className="p-3 font-medium text-gray-900">
                                            {member.firstName} {member.lastName}
                                        </td>
                                        <td className="p-3 text-gray-600">
                                            {member.email}
                                        </td>
                                        <td className="p-3 text-gray-600">
                                            {member.membershipType}
                                        </td>
                                        <td className="p-3">
                                                <span className="rounded-full bg-green-100 px-3 py-1 text-sm text-green-700">
                                                    {member.status}
                                                </span>
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    )}
                </section>
            </div>
            </main>
        </>
    )
}

export default Members