export interface OverallRetention {
    totalBookers: number
    returningBookers: number
    oneTimeBookers: number
    repeatBookingRate: number
}

export interface ClassRetention {
    classSessionId: number
    className: string
    returningMembers: number
}

export interface MemberRetention {
    id: number
    firstName: string
    lastName: string
    email: string
    membershipType: string
}

export async function getOverallRetention(): Promise<OverallRetention> {
    const response = await fetch(
        'http://localhost:8080/api/retention/overall'
    )

    if (!response.ok) {
        throw new Error('Failed to fetch overall retention')
    }

    return response.json()
}

export async function getClassRetention(): Promise<ClassRetention[]> {
    const response = await fetch(
        'http://localhost:8080/api/retention/classes'
    )

    if (!response.ok) {
        throw new Error('Failed to fetch class retention')
    }

    return response.json()
}

export async function getInactiveMembers(): Promise<MemberRetention[]> {
    const response = await fetch(
        'http://localhost:8080/api/retention/inactive'
    )

    if (!response.ok) {
        throw new Error('Failed to fetch inactive members')
    }

    return response.json()
}

export async function getNeverBookedMembers(): Promise<MemberRetention[]> {
    const response = await fetch(
        'http://localhost:8080/api/retention/never-booked'
    )

    if (!response.ok) {
        throw new Error('Failed to fetch never-booked members')
    }

    return response.json()
}

export async function getCancelledOnlyMembers(): Promise<MemberRetention[]> {
    const response = await fetch(
        'http://localhost:8080/api/retention/cancelled-only'
    )

    if (!response.ok) {
        throw new Error('Failed to fetch cancelled-only members')
    }

    return response.json()
}