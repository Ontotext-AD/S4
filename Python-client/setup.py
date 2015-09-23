try:
    from setuptools import setup
except ImportError:
    from distutils.core import setup


with open('README.rst', 'r') as f:
    readme = f.read()

description = ("Ontotext Self-Service Semantic Suite's text annotation SDK for"
               + " Python3. Easily process your text and documents. For more"
               + " information, visit https://console.s4.ontotext.com/")

setup(
    name="s4sdk",
    version="1.1.5",
    author="Svetlin Slavov",
    author_email="svetlin.slavov@ontotext.com",

    packages=["s4sdk", "s4sdk.models"],
    include_package_data=True,

    url="https://pypi.python.org/pypi/s4sdk",
    license="GNU Lesser GPL 2.1",
    description=description,

    long_description=readme,

    install_requires=[
        "requests",
    ],
)
