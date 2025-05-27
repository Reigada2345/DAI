"use client"

import { useState } from "react"
import { Upload, MapPin } from 'lucide-react'
import FileUpload from "@/components/file-upload"
import StopsManager from "@/components/stops-manager"

export default function TransportManager() {
  const [activeTab, setActiveTab] = useState("upload")

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 to-indigo-100">
      <div className="container mx-auto px-4 py-8">
        <div className="mb-8 text-center">
          <h1 className="text-4xl font-bold text-gray-900 mb-2">
            Sistema de Gestão de Transportes DAI
          </h1>
          <p className="text-lg text-gray-600">
            Importe e gira dados de paragens do seu projeto
          </p>
        </div>

        <div className="flex justify-center mb-6">
          <div className="flex bg-white rounded-lg p-1 shadow-sm">
            <button
              onClick={() => setActiveTab("upload")}
              className={`px-4 py-2 rounded-md flex items-center gap-2 ${
                activeTab === "upload"
                  ? "bg-blue-600 text-white"
                  : "text-gray-600 hover:text-gray-900"
              }`}
            >
              <Upload className="h-4 w-4" />
              Importar
            </button>
            <button
              onClick={() => setActiveTab("stops")}
              className={`px-4 py-2 rounded-md flex items-center gap-2 ${
                activeTab === "stops"
                  ? "bg-blue-600 text-white"
                  : "text-gray-600 hover:text-gray-900"
              }`}
            >
              <MapPin className="h-4 w-4" />
              Paragens
            </button>
          </div>
        </div>

        <div className="bg-white rounded-lg shadow-lg p-6">
          {activeTab === "upload" && (
            <div>
              <h2 className="text-2xl font-bold mb-4 flex items-center gap-2">
                <Upload className="h-6 w-6" />
                Importar Ficheiros do Projeto DAI
              </h2>
              <p className="text-gray-600 mb-6">
                Faça upload dos ficheiros stops.txt, bd_dai.sql ou outros ficheiros do projeto
              </p>
              <FileUpload />
            </div>
          )}

          {activeTab === "stops" && (
            <div>
              <h2 className="text-2xl font-bold mb-4 flex items-center gap-2">
                <MapPin className="h-6 w-6" />
                Gestão de Paragens
              </h2>
              <p className="text-gray-600 mb-6">
                Visualize e gira as paragens importadas do projeto DAI
              </p>
              <StopsManager />
            </div>
          )}
        </div>
      </div>
    </div>
  )
}
