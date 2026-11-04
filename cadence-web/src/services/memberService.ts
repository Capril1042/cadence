import type { Member } from '../types/Member'

export interface CreateMemberInput {
    firstName: string
    lastName: string
    email: string
    membershipType: string
}

export async function getAllMembers(): Promise<Member[]> {
    const response = await fetch('http://localhost:8080/api/members')

    if (!response.ok) {
        throw new Error('Failed to fetch members')
    }

    return response.json()
}

export async function addMember(member: CreateMemberInput): Promise<Member> {
    const response = await fetch('http://localhost:8080/api/members', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(member),
    })

    if (!response.ok) {
        throw new Error('Failed to add member')
    }

    return response.json()
}